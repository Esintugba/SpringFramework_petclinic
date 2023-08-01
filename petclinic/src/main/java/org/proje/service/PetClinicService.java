package org.proje.service;

import org.proje.exception.OwnerNotFoundException;
import org.proje.exception.VetNotFoundException;
import org.proje.model.Owner;
import org.proje.model.Vet;

import javax.validation.Valid;
import java.util.List;

public interface PetClinicService {
    List<Owner> findOwners();
    List<Owner> findOwners(String lastName);
    Owner findOwner(Long id) throws OwnerNotFoundException;

    void createOwner(@Valid Owner owner);
    void updateOwner(Owner owner);
    void deleteOwner(Long id);

    List<Vet> findVets();
    Vet findVet(Long id) throws VetNotFoundException;
}
