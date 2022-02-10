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
		moduleRepository.save(module);
		return ResponseEntity.ok(new MessageResponse("User successfully added to module!"));
	}

    //modules
	//add module :

	@PostMapping("")
	public ResponseEntity<?> addModule(@Valid @RequestBody AddModuleRequest addModuleRequest) {
		Module module= new Module(addModuleRequest.getName());
		moduleRepository.save(module);
		return ResponseEntity.ok(new MessageResponse("Module registered successfully!"));
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
		return ResponseEntity.ok(new MessageResponse(omodule.get().toString()));
	}
	@DeleteMapping("/{moduleID}")
	public ResponseEntity<?> deleteModule(@PathVariable long moduleID) {

		Optional<Module> omodule = moduleRepository.findById(moduleID);;
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}
		moduleRepository.delete(omodule.get());
		return ResponseEntity.ok(new MessageResponse("Module deleted "));
	}
	//Ressources :
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
		return ResponseEntity.ok(new MessageResponse("cours registered successfully!"));
	}
	//get All cours pour un module
	@GetMapping("/{moduleID}/Ressources/cours")
	public ResponseEntity<?> getcours(@PathVariable long moduleID) {

		Optional<Module> omodule=moduleRepository.findById(moduleID);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}

		return ResponseEntity.ok(omodule.get().getCours().toString());
	}
	//get All cours pour un module

	//Ressources :
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
		return ResponseEntity.ok(new MessageResponse("questionnaire registered successfully!"));
	}

	@GetMapping("/{moduleID}/Ressources/questionnaire")
	public ResponseEntity<?> getQuest(@PathVariable long moduleID) {

		Optional<Module> omodule=moduleRepository.findById(moduleID);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such Module!"));
		}

		return ResponseEntity.ok(omodule.get().getQuestionnaires().toString());
	}



	}






	//



