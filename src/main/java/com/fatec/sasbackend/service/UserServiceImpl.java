package com.fatec.sasbackend.service;

import com.fatec.sasbackend.converter.CrasConverter;
import com.fatec.sasbackend.converter.RoleConverter;
import com.fatec.sasbackend.converter.UserConverter;
import com.fatec.sasbackend.dto.*;
import com.fatec.sasbackend.entity.Cras;
import com.fatec.sasbackend.entity.Role;
import com.fatec.sasbackend.entity.User;
import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import com.fatec.sasbackend.repository.CrasRepository;
import com.fatec.sasbackend.repository.RoleRepository;
import com.fatec.sasbackend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
        return repository.findAll(pageable)
                .map(user -> converter.fromEntityToDto(new UserDTO(), user));
    }

    @Override
    public Page<UserDTO> findPagedUsersByFilter(String name, String cras, Pageable pageable) {
        return repository.findPagedUsersByFilter(
                        name.toLowerCase(),
                        cras,
                        pageable
                )
                .map(user -> converter.fromEntityToDto(new UserDTO(), user));
    }

    @Override
    public UserSelectOptionsDTO findSelectOptions() {
        List<RoleDTO> roleDTOS = roleRepository
                .findAll()
                .stream()
                .distinct()
                .map(role -> roleConverter.fromEntityToDto(new RoleDTO(), role))
                .sorted(Comparator.comparing(RoleDTO::getName).reversed())
                .toList();

        List<CrasDTO> crasDTOS = crasRepository
                .findAll()
                .stream()
                .distinct()
                .map(cras -> crasConverter.fromEntityToDto(new CrasDTO(), cras))
                .sorted(Comparator.comparing(CrasDTO::getName).reversed())
                .toList();

        return UserSelectOptionsDTO.builder()
                .roles(roleDTOS)
                .cras(crasDTOS)
                .build();
    }

    @Override
    public UserDTO findUserById(Long userId) {
        return repository.findById(userId)
                .map(user -> converter.fromEntityToDto(new UserDTO(), user))
                .orElseThrow(() -> new NotFoundException("User ID " + userId + " not found"));
    }

    @Override
    public UserRegisterDTO registerUser(UserRegisterDTO dto) {
        if(repository.checkIfUsernameAlreadyTaken(dto.getUsername())){
            throw new AlreadyExistsException("Username Already taken");
        }

        User user = User.builder()
                .id(0L)
                .name(dto.getName())
                .password(encoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .roles(roleConverter.fromDtoToEntity(new Role(), dto.getRoles()))
                .cras(crasConverter.fromDtoToEntity(new Cras(), dto.getCras()))
                .build();

        repository.save(user);

        return dto;
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO dto) {
        if (Objects.isNull(dto.getId())) {
            throw new BadRequestException("User ID cannot be null");
        }

        if (repository.checkIfUsernameAlreadyTakenToUpdate(dto.getUsername(), dto.getId())) {
            throw new AlreadyExistsException("Username is already taken");
        }

        User entity = repository.findById(dto.getId())
                .map(user -> converter.fromDtoToEntity(user, dto))
                .orElseThrow(() -> new NotFoundException("User not found!"));

        return converter.fromEntityToDto(dto, entity);
    }
}
