package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.entity.AddressBook;

import java.util.List;

public interface AddressBookService extends IService<AddressBook> {
    void setAllDefaultToZero(AddressBook addressBook);

    List<AddressBook> showAddressInList(AddressBook addressBook);

    AddressBook getDefault();
}
