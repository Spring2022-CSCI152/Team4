package com.CSCI152.team4.server.Repos;

import com.CSCI152.team4.server.Auth.Classes.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends CrudRepository<Token, String> {
}
