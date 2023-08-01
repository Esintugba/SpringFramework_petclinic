package org.proje.web;

import org.proje.exception.InternalServerException;
import org.proje.exception.OwnerNotFoundException;
import org.proje.model.Owner;
import org.proje.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {

    @Autowired
    private PetClinicService petClinicService;

    @DeleteMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOwner(@PathVariable("id") Long id){
       try {
           petClinicService.findOwner(id);
           petClinicService.deleteOwner(id);
       }catch (OwnerNotFoundException ex){
           throw ex;
       }catch (Exception ex){
          throw new InternalServerException(ex);
       }

    }


    @PutMapping("/owner/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @RequestBody Owner ownerRequest){
        try {
            Owner owner= petClinicService.findOwner(id);
            owner.setFirstName(ownerRequest.getFirstName());
            owner.setLastName(ownerRequest.getLastName());
            petClinicService.updateOwner(owner);
            return ResponseEntity.ok().build();
        }
        catch (OwnerNotFoundException ex){
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/owner")
    public ResponseEntity<URI> createOwner(@RequestBody Owner owner){
         try {
             petClinicService.createOwner(owner);
             Long id=owner.getId();
             URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
             return ResponseEntity.created(location).build();
         } catch (ConstraintViolationException ex){
             return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
         }
         catch (Exception ex){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }
    }

    @GetMapping(value = "/owner/{id}",produces = "application/json")
    public ResponseEntity<?> getOwnerAsHateoasResource(@PathVariable("id") Long id){
        try {
            Owner owner=petClinicService.findOwner(id);
            Link self= ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/"+id).withSelfRel();
            Link create=ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner").withRel("create");
            Link update=ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/"+id).withRel("update");
            Link delete=ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/"+id).withRel("delete");
            Resource<Owner> resource=new Resource<Owner>(owner,self,create,update,delete);

            return ResponseEntity.ok(owner);
        }
        catch (OwnerNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @Cacheable("allOwners")
    @GetMapping("/owners")
    public ResponseEntity<List<Owner>> getOwners(){
        System.out.println(">>>inside getOwners...");
        List<Owner> owners=petClinicService.findOwners();
        return ResponseEntity.ok(owners);
    }
    @GetMapping("/owner")
    public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String lastName){
        List<Owner> owners=petClinicService.findOwners(lastName);
        return ResponseEntity.ok(owners);
    }
    @GetMapping("/owner/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id){
        try {
            Owner owner=petClinicService.findOwner(id);
            return ResponseEntity.ok(owner);
        }
        catch (OwnerNotFoundException ex){
            return ResponseEntity.notFound().build();
        }

    }
}
