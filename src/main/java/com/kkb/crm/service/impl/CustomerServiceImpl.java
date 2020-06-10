package com.kkb.crm.service.impl;

import com.kkb.crm.dao.CrmCustomerMapper;
import com.kkb.crm.dao.CrmDictMapper;
import com.kkb.crm.dto.CustomerQuery;
import com.kkb.crm.pojo.CrmCustomer;
import com.kkb.crm.pojo.CrmCustomerExample;
import com.kkb.crm.service.CustomerService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CrmCustomerMapper crmCustomerMapper;
    @Autowired
    private CrmDictMapper dictMapper;

    @Override
    public List<CrmCustomer> selectCustomerList(CustomerQuery customerQuery) {

        //根据查询对象  进行条件查询
        CrmCustomerExample crmCustomerExample = new CrmCustomerExample();
        CrmCustomerExample.Criteria criteria = crmCustomerExample.createCriteria();

        //名称
        if (StringUtils.isNotEmpty(customerQuery.getCustName())){
            criteria.andCustNameLike("%"+customerQuery.getCustName()+"%");
        }
        //行业
        if (StringUtils.isNotEmpty(customerQuery.getCustIndustry())){
            criteria.andCustIndustryEqualTo(customerQuery.getCustIndustry());
        }
        //来源
        if (StringUtils.isNotEmpty(customerQuery.getCustSource())){
            criteria.andCustSourceEqualTo(customerQuery.getCustSource());
        }
        //级别
        if (StringUtils.isNotEmpty(customerQuery.getCustLevel())){
            criteria.andCustLevelEqualTo(customerQuery.getCustLevel());
        }


        List<CrmCustomer> crmCustomerList = crmCustomerMapper.selectByExample(crmCustomerExample);
        for (CrmCustomer crmcustomer : crmCustomerList
        ) {
            String fromtype = dictMapper.selectByPrimaryKey(crmcustomer.getCustSource()).getDictItemName();
            String level = dictMapper.selectByPrimaryKey(crmcustomer.getCustLevel()).getDictItemName();
            String industry = dictMapper.selectByPrimaryKey(crmcustomer.getCustIndustry()).getDictItemName();
            crmcustomer.setCustIndustry(industry);
            crmcustomer.setCustSource(fromtype);
            crmcustomer.setCustLevel(level);
        }
        return crmCustomerList;
    }
}
