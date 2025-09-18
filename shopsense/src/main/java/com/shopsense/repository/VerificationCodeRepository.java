package com.shopsense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
	
	Optional<VerificationCode> findByUserIdAndCode(int userId, int code);
	
	@Modifying
	@Query("DELETE FROM VerificationCode v WHERE v.userId = :userId")
	void deleteByUserId(@Param("userId") int userId);
	
	boolean existsByUserIdAndCode(int userId, int code);
}
