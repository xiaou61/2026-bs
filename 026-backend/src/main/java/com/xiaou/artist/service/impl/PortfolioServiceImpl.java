package com.xiaou.artist.service.impl;

import com.xiaou.artist.entity.Portfolio;
import com.xiaou.artist.mapper.PortfolioMapper;
import com.xiaou.artist.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    
    @Autowired
    private PortfolioMapper portfolioMapper;
    
    @Override
    public Portfolio addPortfolio(Portfolio portfolio) {
        portfolioMapper.insert(portfolio);
        return portfolio;
    }
    
    @Override
    public List<Portfolio> getPortfoliosByArtistId(Long artistId) {
        return portfolioMapper.selectByArtistId(artistId);
    }
    
    @Override
    public List<Portfolio> getAllPortfolios() {
        return portfolioMapper.selectAll();
    }
    
    @Override
    public List<Portfolio> getFeaturedPortfolios() {
        return portfolioMapper.selectFeatured();
    }
    
    @Override
    public Portfolio getPortfolioById(Long id) {
        Portfolio portfolio = portfolioMapper.selectById(id);
        if (portfolio != null) {
            portfolioMapper.incrementViewCount(id);
        }
        return portfolio;
    }
    
    @Override
    public boolean updatePortfolio(Portfolio portfolio) {
        return portfolioMapper.update(portfolio) > 0;
    }
    
    @Override
    public boolean deletePortfolio(Long id) {
        return portfolioMapper.deleteById(id) > 0;
    }
}
