package com.xiaou.artist.service;

import com.xiaou.artist.entity.Portfolio;
import java.util.List;

public interface PortfolioService {
    Portfolio addPortfolio(Portfolio portfolio);
    List<Portfolio> getPortfoliosByArtistId(Long artistId);
    List<Portfolio> getAllPortfolios();
    List<Portfolio> getFeaturedPortfolios();
    Portfolio getPortfolioById(Long id);
    boolean updatePortfolio(Portfolio portfolio);
    boolean deletePortfolio(Long id);
}
