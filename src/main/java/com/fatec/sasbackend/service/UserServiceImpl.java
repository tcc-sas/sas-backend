package com.fatec.sasbackend.service;

import com.fatec.sasbackend.converter.CrasConverter;
import com.fatec.sasbackend.converter.RoleConverter;
import com.fatec.sasbackend.converter.UserConverter;
import com.fatec.sasbackend.dto.CrasDTO;
import com.fatec.sasbackend.dto.RoleDTO;
import com.fatec.sasbackend.dto.UserDTO;
import com.fatec.sasbackend.dto.UserSelectOptionsDTO;
import com.fatec.sasbackend.entity.Cras;
import com.fatec.sasbackend.entity.Role;
import com.fatec.sasbackend.entity.User;
import com.fatec.sasbackend.enums.ERole;
import com.fatec.sasbackend.model.user.UserRegisterModel;
import com.fatec.sasbackend.model.user.UserUpdateModel;
import com.fatec.sasbackend.repository.CrasRepository;
import com.fatec.sasbackend.repository.RoleRepository;
import com.fatec.sasbackend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserConverter converter;
    private final RoleRepository roleRepository;
    private final CrasRepository crasRepository;
    private final CrasConverter crasConverter;
    private final RoleConverter roleConverter;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, UserConverter converter, RoleRepository roleRepository, CrasRepository crasRepository, CrasConverter crasConverter, RoleConverter roleConverter, PasswordEncoder encoder) {
        this.repository = repository;
        this.converter = converter;
        this.roleRepository = roleRepository;
        this.crasRepository = crasRepository;
        this.crasConverter = crasConverter;
        this.roleConverter = roleConverter;
        this.encoder = encoder;
    }

    @Override
    public Page<UserDTO> findAllUsers(Pageable pageable) {
        List<UserDTO> userDTO = repository.findAll(pageable)
                .stream()
                .distinct()
                .map(user -> converter.fromEntityToDto(new UserDTO(), user))
                .toList();

        return new PageImpl<>(userDTO);
    }

    @Override
    public Page<UserDTO> findPagedUsersByFilter(String name, String cras, Pageable pageable) {
        List<UserDTO> userDTO = repository.findPagedUsersByFilter(
                        name.toLowerCase(),
                        cras,
                        pageable
                ).stream()
                .distinct()
                .map(user -> converter.fromEntityToDto(new UserDTO(), user))
                .toList();

        return new PageImpl<>(userDTO);
    }

    @Override
    public UserSelectOptionsDTO findSelectOptions() {
        List<RoleDTO> roleDTOS = roleRepository
                .findAll()
                .stream()
                .distinct()
                .sorted(Comparator.comparing(Role::getName).reversed())
                .map(role -> roleConverter.fromEntityToDto(new RoleDTO(), role))
                .toList();

        List<CrasDTO> crasDTOS = crasRepository
                .findAll()
                .stream()
                .distinct()
                .sorted(Comparator.comparing(Cras::getName).reversed())
                .map(cras -> crasConverter.fromEntityToDto(new CrasDTO(), cras))
                .toList();

        return UserSelectOptionsDTO.builder()
                .roles(roleDTOS)
                .cras(crasDTOS)
                .build();
    }

    @Override
    public UserRegisterModel findUserById(Long userId) {
        return repository.findById(userId).map(user ->
                UserRegisterModel.builder()
                        .userId(user.getId())
                        .name(user.getName())
                        .username(user.getUsername())
                        .cras(crasConverter.fromEntityToDto(new CrasDTO(), user.getCras()))
                        .roles(roleConverter.fromEntityToDto(new RoleDTO(), user.getRoles()))
                        .build()
        ).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public Boolean registerUser(UserRegisterModel model) {
        if (Boolean.TRUE.equals(repository.existsByUsername(model.getUsername()))) {
            return false;
        }


        Role roles = findRoleById(model.getRoles());
        Cras cras = findCrasById(model.getCras());


        User user = new User(0L, model.getUsername(), model.getName(), encoder.encode(model.getPassword()), cras, roles);

        repository.save(user);

        return true;
    }

    public Boolean updateUser(UserUpdateModel model) {
        if (Objects.isNull(model.getUserId())) {
            return false;
        }

        if (Boolean.TRUE.equals(
                repository.findSingleUsername(
                        model.getUsername(),
                        model.getUserId()).isPresent())
        ) {
            return false;
        }

        User user = repository.findById(model.getUserId()).get();

        //REFATORAR O SET DE ROLES - NAO TA DAORA E NUNCA ESTEVE

        user.setName(model.getName());
        user.setUsername(model.getUsername());
        user.setRoles(
               roleConverter.fromDtoToEntity(new Role(), model.getRoles())
        );
        user.setCras(
                crasConverter.fromDtoToEntity(new Cras(), model.getCras())
        );

        repository.save(user);

        return true;
    }

    private Set<Role> findRoleByName(RoleDTO role) {
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));

        if (role.getName().equalsIgnoreCase("admin")) {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(adminRole);
            roles.add(userRole);
        } else {
            roles.add(userRole);
        }

        return roles;
    }

    private Cras findCrasById(CrasDTO crasDTO) {
        Cras cras = null;

        if (crasDTO.getId() != null) {
            cras = crasRepository.findById(crasDTO.getId()).orElseThrow(
                    () -> new RuntimeException("Error: Cras not found")
            );
        }
        return cras;
    }

    private Role findRoleById(RoleDTO roleDTO) {
        Role role = null;

        if (roleDTO.getId() != null) {
            role = roleRepository.findById(roleDTO.getId()).orElseThrow(
                    () -> new RuntimeException("Error: Role not found")
            );
        }
        return role;
    }
}
