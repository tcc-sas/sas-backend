package com.fatec.sasbackend.converter;

import com.fatec.sasbackend.dto.RoleDTO;
import com.fatec.sasbackend.entity.Role;

import java.util.Set;

public interface RoleConverter extends Converter<Role, RoleDTO>{
    Role getFirstRole(Set<Role> rolesSet);
}
