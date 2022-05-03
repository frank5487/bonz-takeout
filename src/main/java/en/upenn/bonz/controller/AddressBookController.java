package en.upenn.bonz.controller;

import en.upenn.bonz.common.BaseContext;
import en.upenn.bonz.common.R;
import en.upenn.bonz.entity.AddressBook;
import en.upenn.bonz.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook) {
        log.info("addressBook: {}", addressBook);

        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);

        return R.success(addressBook);
    }

    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        log.info("addressBook: {}", addressBook);

        addressBookService.setAllDefaultToZero(addressBook);

        addressBook.setIsDefault(1);

        addressBookService.updateById(addressBook);

        return R.success(addressBook);
    }

    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        }

        return R.error("address does not exist...");
    }

    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        AddressBook addressBook = addressBookService.getDefault();

        if (addressBook != null) {
            return R.success(addressBook);
        }

        return R.error("default address does not exist");
    }

    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook: {}", addressBook);

        List<AddressBook> addressBookList = addressBookService.showAddressInList(addressBook);

        return R.success(addressBookList);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam Long ids) {
        log.info("ids: {}", ids);

        addressBookService.removeById(ids);

        return R.success("delete address...");
    }

    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook) {
        log.info("addressBook: {}", addressBook);

        addressBookService.updateById(addressBook);

        return R.success("update the addressBook...");
    }
}
