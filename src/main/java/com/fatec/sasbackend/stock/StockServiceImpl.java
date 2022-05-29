package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockConverter stockConverter;

    @Override
    public Page<StockDTO> findAllStock(Pageable pageable) {
        return stockRepository.findAll(pageable)
                .map(stock -> stockConverter.fromEntityToDto(new StockDTO(), stock));

    }

    @Override
    public Page<StockDTO> findPagedStockByFilter(Pageable pageable, String stockId, String name) {
//        return stockRepository.findPagedStocksByFilter(
//                        stockId,
//                        name.toLowerCase(),
//                        pageable)
//                .map(stock -> stockConverter.fromEntityToDto(new StockDTO(), stock));

        return null;
    }

    @Override
    public StockDTO findStockById(Long id) {
        return stockRepository.findById(id)
                .map(stock -> stockConverter.fromEntityToDto(new StockDTO(), stock))
                .orElseThrow(() -> new NotFoundException("Stock not found!"));
    }

    @Override
    @Transactional
    public StockDTO updateStock(StockDTO dto) {
        if(Objects.isNull(dto.getId())){
            throw new BadRequestException("Stock ID is null");
        }

        if(Boolean.TRUE.equals(stockRepository.checkIfNameIsAlreadyTakenToUpdate(dto.getId(), dto.getName()))){
            throw new AlreadyExistsException("Stock with name " + dto.getName() + " is already taken");
        }

        Stock entity = stockRepository.findById(dto.getId())
                .map(stock -> stockConverter.fromDtoToEntity(stock, dto))
                .orElseThrow(( ) -> new NotFoundException("Stock not found"));

        return stockConverter.fromEntityToDto(dto, entity);
    }

    @Override
    public StockDTO registerStock(StockDTO dto) {
        if(Boolean.TRUE.equals(stockRepository.checkIfNameIsAlreadyTaken(dto.getName()))){
            throw new AlreadyExistsException("Username Already taken");
        }

        Stock entity = stockRepository.findById(dto.getId())
                .map(stock -> stockConverter.fromDtoToEntity(stock, dto))
                .orElseThrow(( ) -> new NotFoundException("Stock not found"));

        stockRepository.save(entity);
        return dto;
    }
}
