package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long> {
    Function findByFunctionName(String functionName);
}
