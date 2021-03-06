package com.kkb.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kkb.crm.dto.CustomerQuery;
import com.kkb.crm.pojo.CrmCustomer;
import com.kkb.crm.pojo.CrmDict;
import com.kkb.crm.service.CustomerService;
import com.kkb.crm.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
public class CustomerController {
    @Value("${fromType}")
    private String fromTypecode;
    @Value("${levelType}")
    private String levelType;
    @Value("${industryType}")
    private String industryType;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private DictService dictService;

    @RequestMapping("/customerList")
    public String showCustomerList(Model model, CustomerQuery customerQuery) {
        PageInfo<CrmCustomer> pageInfo = customerService.selectCustomerList(customerQuery);
        model.addAttribute("pages", pageInfo.getPages());
        List<CrmCustomer> customers = pageInfo.getList();
        model.addAttribute("customers", customers);
        List<CrmDict> fromType = dictService.selectDictByTypeCode(fromTypecode);
        model.addAttribute("fromType", fromType);
        List<CrmDict> industry = dictService.selectDictByTypeCode(industryType);
        model.addAttribute("industryType", industry);
        List<CrmDict> customerLevel = dictService.selectDictByTypeCode(levelType);
        model.addAttribute("levelType", customerLevel);

        return "customer";
    }

    @ResponseBody
    @RequestMapping("/customer/edit")
    public CrmCustomer selectUserById(Integer id) {
        return customerService.selectUserById(id);
    }

    @ResponseBody
    @RequestMapping("/customer/update")
    public String UpdateUser(CrmCustomer crmCustomer) {
        customerService.updateUser(crmCustomer);
        return "{\"status\":\"ok\"}";
    }
    @ResponseBody
    @RequestMapping("/customer/delete")
    public String DeleteUser(Integer id) {
        customerService.DeleteUser(id);
        return "{\"status\":\"ok\"}";
    }
}
