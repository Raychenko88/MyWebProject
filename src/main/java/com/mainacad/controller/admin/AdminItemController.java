package com.mainacad.controller.admin;


import com.mainacad.dao.ItemDAO;
import com.mainacad.model.Item;
import com.mainacad.service.ItemService;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/item-admin")
public class AdminItemController extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        RequestDispatcher dispatcher;
        String action = req.getParameter("action");

        if (action.equals("add-item")){
            String itemCode = req.getParameter("item-code");
            String strPrice = req.getParameter("price");
            Integer price = Integer.valueOf(strPrice);
            String name = req.getParameter("name");
            String strAvailability = req.getParameter("availability");
            Integer availability = Integer.valueOf(strAvailability);
            List <Item> list = ItemService.findByItemCode(itemCode);
            if (!list.isEmpty()){
                req.setAttribute("errorMsg", "Such a item already registered");
            }else {
                Item item1 = new Item(name,itemCode, price, availability);
                ItemService.save(item1);
                req.setAttribute("errorMsg", "Item registered");
            }
            dispatcher = req.getRequestDispatcher("/jsp/admin.jsp");
            dispatcher.forward(req,resp);
        }

       else if (action.equals("delete-item")){
            String itemCode = req.getParameter("item-code");
            List <Item> list = ItemService.findByItemCode(itemCode);
            if (!list.isEmpty()){
                for (Item item : list){
                    ItemService.delete(item.getId());
                }
                req.setAttribute("errorMsg", "Item deleted");
                dispatcher = req.getRequestDispatcher("/jsp/admin.jsp");
                dispatcher.forward(req,resp);
            }else {
                req.setAttribute("errorMsg", "There is no such thing");
                dispatcher = req.getRequestDispatcher("/jsp/admin.jsp");
                dispatcher.forward(req,resp);
            }
        }
    }
}