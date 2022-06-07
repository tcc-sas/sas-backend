package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<StockDTO> saveInStock(List<StockDTO> dto) {




        return dto;
    }
}
