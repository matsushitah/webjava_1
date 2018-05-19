package jp.co.systena.tigerscave.SpringHelloSystena.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.SpringHelloSystena.application.model.ListForm;
import jp.co.systena.tigerscave.SpringHelloSystena.application.model.ListService;;

@Controller
public class ListController {

  @Autowired
  HttpSession session;

  @RequestMapping(value = "/ListView", method = RequestMethod.GET) // URLとのマッピング
  public ModelAndView show(ModelAndView mav) {

 // Viewに渡すデータを設定
    ListForm listForm = (ListForm) session.getAttribute("form");
    session.removeAttribute("form");

    if (listForm != null) {
      HashMap order = listForm.getOrder();
      mav.addObject("message", order.get("name") +"を"+ order.get("num") +"個カートに追加しました。");
    }
    mav.addObject("listForm", new ListForm());  // 新規クラスを設定

    ListService listService = new ListService();
    List itemList = new ArrayList();
    itemList = listService.getItemList();
    mav.addObject("itemList", itemList);

    List cart = (List) session.getAttribute("cart");
    if( cart == null) {
        cart = new ArrayList();
        session.setAttribute("cart", cart);
    }

    mav.addObject("cart", cart);

    BindingResult bindingResult = (BindingResult) session.getAttribute("result");
    if (bindingResult != null) {
      mav.addObject("bindingResult", bindingResult);
    }

    mav.setViewName("ListView");
    return mav; // 文字列を返却

  }

  @RequestMapping(value="/ListView", method = RequestMethod.POST)  // URLとのマッピング
  private ModelAndView order(ModelAndView mav, @Valid ListForm listForm, BindingResult bindingResult, HttpServletRequest request) {

    List cart = (List)session.getAttribute("cart");
    if( cart == null) {
        cart = new ArrayList();
        session.setAttribute("cart", cart);
    }

    ListService listService = new ListService();
    List itemList = new ArrayList();
    itemList = listService.getItemList();
    mav.addObject("itemList", itemList);

    String name = request.getParameter("name");
    String numStr = request.getParameter("num");
    int num = Integer.parseInt(numStr);

    listForm.setOrder(name, num);

    HashMap order = listForm.getOrder();



    cart = listService.addCart(cart, order);

    mav.addObject(cart);

    // データをセッションへ保存
    session.setAttribute("form", listForm);
    session.setAttribute("cart", cart);
    return new ModelAndView("redirect:/ListView");        // リダイレクト
  }
}
