package com.codegym.dto.service;

import com.codegym.dto.entity.User;
import com.codegym.dto.genneric.genericService;

public interface IUserService extends genericService<User> {
    boolean isDeleted(Long id) throws Exception;
}
