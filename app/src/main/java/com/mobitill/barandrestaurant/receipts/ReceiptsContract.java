package com.mobitill.barandrestaurant.receipts;

import com.mobitill.barandrestaurant.BasePresenter;
import com.mobitill.barandrestaurant.BaseView;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;

/**
 * Created by andronicus on 5/22/2017.
 */

public interface ReceiptsContract {

    interface View extends BaseView<Presenter>{

        void showProducts(List<Product> products);

        void checkedOut();

        void notCheckedOut();

    }
    interface Presenter extends BasePresenter{

        void isCheckedOut();

        void isNotCheckedOut();

    }
}
