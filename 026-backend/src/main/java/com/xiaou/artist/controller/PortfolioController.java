package com.xiaou.artist.controller;

import com.xiaou.artist.common.Result;
import com.xiaou.artist.entity.Portfolio;
import com.xiaou.artist.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    
    @Autowired
    private PortfolioService portfolioService;
    
    @PostMapping("/add")
    public Result<Portfolio> addPortfolio(@RequestBody Portfolio portfolio) {
        Portfolio newPortfolio = portfolioService.addPortfolio(portfolio);
        return Result.success(newPortfolio);
    }
    
    @GetMapping("/artist/{artistId}")
    public Result<List<Portfolio>> getPortfoliosByArtistId(@PathVariable Long artistId) {
        List<Portfolio> portfolios = portfolioService.getPortfoliosByArtistId(artistId);
        return Result.success(portfolios);
    }
    
    @GetMapping("/list")
    public Result<List<Portfolio>> getAllPortfolios() {
        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        return Result.success(portfolios);
    }
    
    @GetMapping("/featured")
    public Result<List<Portfolio>> getFeaturedPortfolios() {
        List<Portfolio> portfolios = portfolioService.getFeaturedPortfolios();
        return Result.success(portfolios);
    }
    
    @GetMapping("/{id}")
    public Result<Portfolio> getPortfolioById(@PathVariable Long id) {
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        return Result.success(portfolio);
    }
    
    @PutMapping("/update")
    public Result<String> updatePortfolio(@RequestBody Portfolio portfolio) {
        boolean success = portfolioService.updatePortfolio(portfolio);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deletePortfolio(@PathVariable Long id) {
        boolean success = portfolioService.deletePortfolio(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}
