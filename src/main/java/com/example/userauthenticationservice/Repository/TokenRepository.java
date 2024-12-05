package com.example.userauthenticationservice.Repository;

import com.example.userauthenticationservice.models.State;
import com.example.userauthenticationservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token getTokenByUser_IdAndState(Long id, State l);
}
