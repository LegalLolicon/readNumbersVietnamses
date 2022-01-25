package hrm;

public class ReadBillions {

    String[] numWords = {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"}; // set nó làm biến global vì sử dụng lại nhiều lần trong nhiều method


    boolean isAllZero(String a){ // method kiểm tra xem đuôi của một số đã cho có chữa toàn sô 0 hay không
        boolean found = true; // gia tri mac dinh la true
        char[] checkZero = a.toCharArray(); // tách ra rồi nhét vào array để kiểm tra cho dễ
        for (char check : checkZero){
            if(Integer.parseInt(String.valueOf(check)) != 0){  // chạy lần lượt qua từng số, nếu nhận được giá trị khác 0 => gia trị là false => cho cút luôn
                found = false;
                break;
            }
        }
        return found;
    }

    String readAllZeros(String a) { // đếm số trong trường hợp số đuôi chỉ có số 0 (trường hợp không phải số 1 và 0)
        String read;
        String [] numWords = {"mươi", "trăm", "nghìn", " mươi nghìn", "trăm nghìn", "triệu", "mươi triệu", "trăm triệu", "tỉ", "mươi tỉ", "trăm tỉ" };
        read = numWords[a.substring(1).length() -1 ]; // đếm xem có bao nhiêu số không rồi trừ đi một giá trị để match vào array rồi xuất ra
        return read;
    }

    String readAllZeros1(String a){ // dành cho trường hợp sô đuôi chỉ có số 0 với số 1 đứng đầu tiên
        String read;
        String [] withOne = {"mười", "một trăm", "một nghìn", "mười nghìn" , "một trăm nghìn" , "một triệu" , "mười triệu", "một trăm triệu", "một tỉ" , "mười tỉ", "một trăm tỉ"};
        read = withOne[a.substring(1).length() - 1]; // đếm xem có bao nhiêu số không rồi trừ đi một giá trị sau đó xuất giá trị match theo array
        return read;
    }

    String specCondition(String count) { // method này để đọc các trường hợp đặc biệt khi không có số 0 và số 1 đứng đầu
        String result;
        int i = Integer.parseInt(String.valueOf(count.charAt(0))); // các biến được lưu vào để gọi cho gọn code
        int i1 = Integer.parseInt(String.valueOf(count.charAt(2)));
        int i2 = Integer.parseInt(String.valueOf(count.charAt(1)));
        if (count.charAt(1) == '0') { // xét trường hợp số 0 ở giữa
            result = count.endsWith("0") ? numWords[i] + " trăm " : (count.endsWith("4") ? numWords[i] + " trăm lẻ tư " :  numWords[i] + " trăm lẻ " + numWords[i1]);
            // xét các trường hợp: kết thúc với sô 0 => trăm, tương tự với các trường hợp dặc biệt còn lại.
        } else if (count.charAt(1) == '1') { // xét trường hợp số 1 ở giữa
            result = count.endsWith("0") ? numWords[i] + " trăm mười" : (count.endsWith("5") ? numWords[i] + " trăm mười lăm" : numWords[i] + " tram muoi " + numWords[i1]);
            // cũng như trên
        } else { // còn lại là trường hợp bình thường khi đọc
            result = count.endsWith("0") ? numWords[i] + " trăm " + numWords[i2] + " mươi" : (count.endsWith("1") ? numWords[i] + " trăm " + numWords[i2] + " mốt" :
                            (count.endsWith("4")) ?  numWords[i] + " trăm " + numWords[i2] + " tư" :
                                    (count.endsWith("5")) ? numWords[i] + " trăm " + numWords[i2] + " lăm" : numWords[i] + " trăm " + numWords[i2] + " " + numWords[i1  ]);
        }
        return result;
    }

    String readThreeNums(String count){
        String result;
        if (count.startsWith("0")){ // xét trường hợp số 0 ở đầu
            result = isAllZero(count) ? "" : specCondition(count);
        } else if (count.startsWith("1")) { // xét trường hợp số 1 ở đầu
            result = isAllZero(count.substring(1)) ? readAllZeros1(count) : specCondition(count);
        } else {
            result = isAllZero(count.substring(1)) ? numWords[Integer.parseInt(String.valueOf(count.charAt(0)))] + " " + readAllZeros(count) : specCondition(count);
        }
        return result;
    }

    String readDynamic (String count) { // method này được sử dụng trong trường hợp số có thể là 1 chữ số cho đến 3 chữ số
        String result = "";
        if (count.length() == 1) {
            char[] split = count.toCharArray();
            for (char num : split) {
                result = numWords[Integer.parseInt(String.valueOf(num))];
            }
        } else if (count.length() == 2) {
            int i = Integer.parseInt(String.valueOf(count.charAt(1)));
            int i1 = Integer.parseInt(String.valueOf(count.charAt(0)));
            if (count.startsWith("1")) { // xét trường hợp số đầu tiên là 1
                result = isAllZero(count.substring(1)) ? readAllZeros1(count) : (count.endsWith("5") ? "mười lăm" : "mười " + numWords[i]);
            } else {
                result = isAllZero(count.substring(1)) ? numWords[i1] + " " + readAllZeros(count) : (count.endsWith("1") ?  numWords[i1] + " mốt" :
                        (count.endsWith("4")) ?  numWords[i1] + " tư" : (count.endsWith("5")) ?  numWords[i1] + " lăm" :  numWords[i1] + " " +  numWords[i]);
            }
        } else {
            result = readThreeNums(count);
        }
        return result;
    }


    String readLongNumber(String count) {  // method xử lý chính => gọi cái này ra khi dùng class
        String result;
        String bil, mil, thoud, hund; // các biến được định nghĩa sẵn để cho dễ gọi khi cần
       if (count.length() > 12){ // quá 12 số là cho cút luôn
           result = "So qua lon, khong the doc";
       } else {
           if (count.length() < 4) { // đếm từ hàng đơn vị đên hàng trăm
               result = readDynamic(count);
           } else {
               int i1 = Integer.parseInt(String.valueOf(count.charAt(0)));
               if (count.length() < 7){ // đếm từ hàng nghìn đến hàng trăm nghìn
                   if (isAllZero(count.substring(1))){
                       result = count.startsWith("1") ? readAllZeros1(count) : numWords[i1] + " " + readAllZeros(count);
                   } else {
                       thoud = readDynamic(new StringBuilder(new StringBuilder(count).reverse().substring(3)).reverse().toString()); // tách ra 3 số cuối và các số đầu rồi sau đó xử lý
                       hund = readThreeNums(new StringBuilder(new StringBuilder(count).reverse().substring(0,3)).reverse().toString());  // reverse rồi cắt ra, sau đó reverse thêm một lần nữa để lấy số gốc
                       result = hund.equals("") ? thoud + " nghìn" : thoud + " nghìn, " + hund;
                   }
               } else {
                   if (count.length() < 10){ // đếm từ hàng triệu đến hàng trăm triệu
                       if (isAllZero(count.substring(1))){
                           result = count.startsWith("1") ? readAllZeros1(count) : numWords[i1] + " " + readAllZeros(count);
                       } else {
                           mil = readDynamic(new StringBuilder(new StringBuilder(count).reverse().substring(6)).reverse().toString()); // tách ra làm các dãy 3 số và các số đầu để xử lý
                           thoud = readThreeNums(new StringBuilder(new StringBuilder(count).reverse().substring(3,6)).reverse().toString()); // reverse rồi cắt ra, sau đó reverse một lần nữa để lấy số gốc
                           hund = readThreeNums(new StringBuilder(new StringBuilder(count).reverse().substring(0,3)).reverse().toString());
                           result = thoud.equals("") ? (hund.equals("") ? mil + " triệu" : mil + " triệu, " + hund) : mil + " triệu, " + thoud + " nghìn, " + hund;
                       }
                   } else { // đếm từ hàng tỉ đến hàng trăm tỉ
                       if(isAllZero(count.substring(1))){ // xét trường hợp số bắt đầu bằng 1
                           result = count.startsWith("1") ? readAllZeros1(count) : numWords[i1] + " " + readAllZeros(count);
                       } else {
                           bil = readDynamic(new StringBuilder(new StringBuilder(count).reverse().substring(9)).reverse().toString());
                           mil = readThreeNums(new StringBuilder(new StringBuilder(count).reverse().substring(6,9)).reverse().toString()); //tách ra làm các dãy 3 số và các số đầu để xử lý
                           thoud = readThreeNums(new StringBuilder(new StringBuilder(count).reverse().substring(3,6)).reverse().toString()); // đảo ngược rồi cắt ra, sau đó đảo ngược lần nữa để lấy số gốc
                           hund = readThreeNums(new StringBuilder(new StringBuilder(count).reverse().substring(0,3)).reverse().toString());
                           if (mil.equals("")){  // trường hợp hàng triệu không trả về kết quả nào
                               result = thoud.equals("") ? (hund.equals("") ?  bil + " tỉ" :  bil + " tỉ, " + hund) : (hund.equals("") ? bil + " tỉ, " + thoud + " nghìn" : bil + " tỉ, " + thoud + " nghìn, " + hund);
                           } else {
                               result = thoud.equals("") ? (hund.equals("") ? bil + " tỉ, " + mil + " triệu" : bil + " tỉ, " + mil + " triệu, " + hund ) :
                                       (hund.equals("") ?  bil + " tỉ, " + mil + " triệu, " + thoud + " nghìn" : bil + " tỉ, " + mil + " triệu, " + thoud + " nghìn, " + hund);
                           }
                       }
                   }
               }
           }
       }
        return result;
    }
}