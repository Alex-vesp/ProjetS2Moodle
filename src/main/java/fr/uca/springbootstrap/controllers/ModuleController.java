package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.payload.request.AddModuleRequest;
import fr.uca.springbootstrap.payload.request.AddRessourceRequest;
import fr.uca.springbootstrap.payload.request.SignupRequest;
import fr.uca.springbootstrap.payload.request.addTextRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.*;
import fr.uca.springbootstrap.security.jwt.JwtUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/module")
public class ModuleController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	CoursRepository coursRepository;

	@Autowired
	QuestionnaireRepository questionnaireRepository;


	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/{id}/participants")
	@PreAuthorize("hasRole('TEACHER')")
	public ResponseEntity<?> getPArticipants(@PathVariable long id) {
		Optional<Module> omodule = moduleRepository.findById(id);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such module!"));
		}
		Module module =omodule.get();
		return ResponseEntity.ok(new MessageResponse(module.getParticipants().toString()));
	}

	@PostMapping("/{id}/participants/{userid}")
	@PreAuthorize("hasRole('TEACHER')")
	public ResponseEntity<?> addUser(Principal principal, @PathVariable long id, @PathVariable long userid) {
		Optional<Module> omodule = moduleRepository.findById(id);
		Optional<User> ouser = userRepository.findById(userid);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such module!"));
		}
		if (!ouser.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such user!"));
		}

		Module module = omodule.get();
		User user = ouser.get();
		User actor = userRepository.findByUsername(principal.getName()).get();

		Set<User> participants = module.getParticipants();

		if ((participants.isEmpty() && actor.equals(user))
				|| participants.contains(actor)) {
			participants.add(user);
		} else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Not allowed to add user!"));
		}
		//ligne ajoutée
		participants.add(user);
		moduleRepository.save(module);
		return ResponseEntity.ok(new MessageResponse("User successfully added to module!"));
	}

    //modules
	//add module :

	@PostMapping("")
	@PreAuthorize("hasRole('TEACHER')")
	public ResponseEntity<?> addModule(@Valid @RequestBody AddModuleRequest addModuleRequest) {
		Module module= new Module(addModuleRequest.getName());
		moduleRepository.save(module);
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("id",module.getId());
	    jsonObject.toString();
		return ResponseEntity.ok(jsonObject.toString());
	}
	@GetMapping("")
	public ResponseEntity<?> getModules() {
		moduleRepository.findAll();
		return ResponseEntity.ok(new MessageResponse(moduleRepository.findAll().toString()));
	}
	@GetMapping("/{moduleID}")
	public ResponseEntity<?> getModule(@PathVariable long moduleID) {

		Optional<Module> omodule = moduleRepository.findById(moduleID);;
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}
		return ResponseEntity.ok(omodule.get().toString());
	}
	@DeleteMapping("/{moduleID}")
	@PreAuthorize("hasRole('TEACHER')")
	public ResponseEntity<?> deleteModule(@PathVariable long moduleID) {

		Optional<Module> omodule = moduleRepository.findById(moduleID);;
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("id",omodule.get().getId());
		jsonObject.toString();
		moduleRepository.delete(omodule.get());
		return ResponseEntity.ok(jsonObject.toString());
	}

	@PutMapping("/{moduleID}")
	@PreAuthorize("hasRole('TEACHER')")
	public ResponseEntity<?> modifieModule(@Valid @RequestBody AddModuleRequest addModuleRequest,@PathVariable long moduleID) {

		Optional<Module> omodule = moduleRepository.findById(moduleID);;
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}
		Module module = omodule.get();
		module.setName(addModuleRequest.getName());
		moduleRepository.save(module);
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("id",omodule.get().getId());
		jsonObject.toString();
		return ResponseEntity.ok(jsonObject.toString());
	}

	//Ressources :
	@PreAuthorize("hasRole('TEACHER')")
	@PostMapping("/{moduleID}/Ressources/cours")
	public ResponseEntity<?> addcours(@Valid @RequestBody AddRessourceRequest addRessourceRequest,@PathVariable long moduleID) {
		Cours cours= new Cours(addRessourceRequest.getName(),addRessourceRequest.getDes());
		coursRepository.save(cours);
		Optional<Module> omodule=moduleRepository.findById(moduleID);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}
		Module module=omodule.get();
		module.getCours().add(cours);
		moduleRepository.save(module);
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("id",cours.getId());
		jsonObject.toString();
		return ResponseEntity.ok(jsonObject.toString());
	}
	//get All cours pour un module
	@GetMapping("/{moduleID}/Ressources/cours")
	public ResponseEntity<?> getcours(Principal principal,@PathVariable long moduleID) {

		Optional<Module> omodule=moduleRepository.findById(moduleID);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}

		//verifie si l utlisateur est bien inscrit au cours :
		Module module=omodule.get();
		User actor = userRepository.findByUsername(principal.getName()).get();
		if(!module.getParticipants().contains(actor)){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Not allowed ! Utilisateur pas inscrit au cours"));

		}


		return ResponseEntity.ok(omodule.get().getCours().toString());
	}
	//get All cours pour un module

	//Ressources :
	@PreAuthorize("hasRole('TEACHER')")
	@PostMapping("/{moduleID}/Ressources/questionnaire")
	public ResponseEntity<?> addquestionnaire(@Valid @RequestBody AddRessourceRequest addRessourceRequest,@PathVariable long moduleID) {
		Questionnaire questionnaire= new Questionnaire(addRessourceRequest.getName(),addRessourceRequest.getDes());
		questionnaireRepository.save(questionnaire);
		Optional<Module> omodule=moduleRepository.findById(moduleID);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}
		Module module=omodule.get();
		module.getQuestionnaires().add(questionnaire);
		moduleRepository.save(module);
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("id",questionnaire.getId());
		return ResponseEntity.ok(jsonObject.toString());
	}

	@GetMapping("/{moduleID}/Ressources/questionnaire")
	public ResponseEntity<?> getQuest(Principal principal,@PathVariable long moduleID) {

		Optional<Module> omodule=moduleRepository.findById(moduleID);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}
		//verifie si l utlisateur est bien inscrit au cours :
		Module module=omodule.get();
		User actor = userRepository.findByUsername(principal.getName()).get();
		if(!module.getParticipants().contains(actor)){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Not allowed ! Utilisateur pas inscrit au cours"));

		}

		return ResponseEntity.ok(omodule.get().getQuestionnaires().toString());
	}



	}






	//



