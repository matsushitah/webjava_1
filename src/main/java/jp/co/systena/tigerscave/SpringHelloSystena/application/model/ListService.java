package jp.co.systena.tigerscave.SpringHelloSystena.application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListService {

  // 商品一覧
  private List itemList = new ArrayList();

  // 商品一覧取得
  public List getItemList() {
    Map item1 = new HashMap();
    item1.put("name", "リンゴ");
    item1.put("price", 100);
    Map item2 = new HashMap();
    item2.put("name", "ぶどう");
    item2.put("price", 150);
    Map item3 = new HashMap();
    item3.put("name", "みかん");
    item3.put("price", 80);
    this.itemList.add(item1);
    this.itemList.add(item2);
    this.itemList.add(item3);
    return this.itemList;
  }

  public List addCart(List cart, HashMap order) {

    Boolean addFlg = true;
    String name = (String) order.get("name");
    int num = (int)order.get("num");
    int price = 0;

    for (int i = 0; i < itemList.size(); i++) {
      HashMap item = (HashMap) itemList.get(i);
      String itemName = (String) item.get("name");
      if (name.equals(itemName)) {
        int itemPrice = (int) item.get("price");
        price = itemPrice * num;
      }
    }

    for (int i = 0; i < cart.size(); i++) {
      HashMap cartItem = (HashMap) cart.get(i);
      String cartItemName = (String) cartItem.get("name");
      if (name.equals(cartItemName)) {
        int cartItemNum = (int)cartItem.get("num");
        int cartItemPrice = (int) cartItem.get("price");
        int addItemNum = num + cartItemNum;
        int addItemPrice = cartItemPrice + price;
        HashMap addCartItem = new HashMap();
        addCartItem.put("name", name);
        addCartItem.put("num", addItemNum);
        addCartItem.put("price", addItemPrice);
        cart.set(i, addCartItem);
        addFlg = false;
        break;
      }
    }

    if (addFlg == true) {
      order.put("price", price);
      cart.add(order);
    }

    return cart;

  }
}
