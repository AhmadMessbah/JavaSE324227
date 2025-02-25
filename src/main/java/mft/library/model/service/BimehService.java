package mft.library.model.service;





import mft.library.model.entity.Bimeh;
import mft.library.model.entity.enums.InsuranceStatus;
import mft.library.model.repository.BimehRepository;

import java.time.LocalDate;
import java.util.List;

public class BimehService {

    // ذخیره بیمه
    public static void save(Bimeh bimeh) throws Exception {

        validateStartAndEndDate(bimeh);

        if (bimeh.getPolicyNumber() == null || bimeh.getPolicyNumber().isEmpty()) {
            throw new Exception("شماره بیمه نمی‌تواند خالی باشد");
        }

        try (BimehRepository bimehRepository = new BimehRepository()) {
            bimehRepository.save(bimeh);
        }
    }

    // ویرایش بیمه
    public static void edit(Bimeh bimeh) throws Exception {
        findById(bimeh.getId());

        // بیمه‌های منقضی‌شده قابل ویرایش نیستند
        if (bimeh.getStatus() == InsuranceStatus.EXPIRED) {
            throw new Exception("بیمه‌های منقضی شده قابل ویرایش نیستند.");
        }

        // اعتبارسنجی تاریخ شروع و پایان
        validateStartAndEndDate(bimeh);

        try (BimehRepository bimehRepository = new BimehRepository()) {
            bimehRepository.edit(bimeh);
        }
    }

    // حذف بیمه
    public static void remove(Integer id) throws Exception {
        findById(id);
        // بررسی اینکه آیا بیمه منقضی شده است
        if (findById(id).getEndDate().isBefore(LocalDate.now())) {
            // اگر تاریخ پایان بیمه قبل از زمان حال باشد، آن را حذف می‌کنیم
            try (BimehRepository bimehRepository = new BimehRepository()) {
                bimehRepository.remove(id);
                System.out.println("بیمه منقضی شده و حذف شد.");
            }
        } else {
            throw new Exception("بیمه‌ای که هنوز منقضی نشده باشد قابل حذف نیست.");
        }
    }

    // دریافت تمام بیمه‌ها
    public static List<Bimeh> findAll() throws Exception {
        try (BimehRepository bimehRepository = new BimehRepository()) {
            List<Bimeh> bimehList = bimehRepository.findAll();
            if (bimehList.isEmpty()) {
                throw new Exception("بیمه‌ای یافت نشد");
            }
            return bimehList;
        }
    }

    // پیدا کردن بیمه بر اساس id
    public static Bimeh findById(Integer id) throws Exception {
        try (BimehRepository bimehRepository = new BimehRepository()) {
            Bimeh bimeh = bimehRepository.findById(id);
            if (bimeh == null) {
                throw new Exception("بیمه‌ای یافت نشد");
            }
            return bimeh;
        }
    }

    // تابع چک کردن تاریخ شروع و پایان
    public static void validateStartAndEndDate(Bimeh bimeh) throws Exception {
        if (bimeh.getStartDate().isAfter(bimeh.getEndDate())) {
            throw new Exception("تاریخ شروع نمی‌تواند بعد از تاریخ پایان باشد");
        }
    }



}



