package org.proje.dao.jpa;

import org.proje.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet,Long> {
}
