package com.babyangel.modules.good.controller;

import java.util.Arrays;
import java.util.Map;

import com.babyangel.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babyangel.modules.good.entity.GoodsEntity;
import com.babyangel.modules.good.service.GoodsService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 商品管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-02 21:43:31
 */
@RestController
@RequestMapping("good/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("good:goods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{goodsId}")
    @RequiresPermissions("good:goods:info")
    public R info(@PathVariable("goodsId") Long goodsId){
        GoodsEntity goods = goodsService.selectById(goodsId);

        return R.ok().put("goods", goods);
    }

    /**
     * 保存
     */
    @Log(title = "物品管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("good:goods:save")
    public R save(@RequestBody GoodsEntity goods){
        goodsService.insert(goods);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "物品管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("good:goods:update")
    public R update(@RequestBody GoodsEntity goods){
        ValidatorUtils.validateEntity(goods);
        goodsService.updateAllColumnById(goods);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "物品管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("good:goods:delete")
    public R delete(@RequestBody Long[] goodsIds){
        goodsService.deleteBatchIds(Arrays.asList(goodsIds));

        return R.ok();
    }

}
