package parsing;

import login.AptPrice;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 지역코드가 포함된 Excel 파일을 파싱하는 클래스
 * @author 장윤재
 * @since 2021년 2월 13일
 */


public class ExcelPs {
	 public String areaCodes ="";
	 public long areaCode;  //우리가 필요한 지역코드
	 public String areaName= "";
	 public String sidoName= "";
		XSSFRow row;
		XSSFCell cell;
		XSSFCell sido;

		@SuppressWarnings("resource")
		public ExcelPs(String areaName, String sidoName) {
			this.areaName = areaName; //지역이름
			this.sidoName = sidoName; //시도이름
			System.out.println(sidoName);
			System.out.println(areaName);
			
		try {
			//엑셀파일의 경로
			//C:\Java\workspace\realEstatePro\src
			FileInputStream inputStream = new FileInputStream("C:\\Java\\workspace\\realEstatePro\\src\\code.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			// sheet수 취득
			int sheetCn = workbook.getNumberOfSheets();
			System.out.println("sheet수 : " + sheetCn);
			for (int cn = 0; cn < sheetCn; cn++) {
				
				
				System.out.println("취득하는 sheet 이름 : " + workbook.getSheetName(cn));
				System.out.println(workbook.getSheetName(cn) + " sheet 데이터 취득 시작");
				// 0번째 sheet 정보 취득	
				XSSFSheet sheet = workbook.getSheetAt(cn);
				// 취득된 sheet에서 rows수 취득
				int rows = sheet.getPhysicalNumberOfRows();
				System.out.println(workbook.getSheetName(cn) + " sheet의 row수 : " + rows);
				// 취득된 row에서 취득대상 cell수 취득
				int cells = sheet.getRow(cn).getPhysicalNumberOfCells(); //
				System.out.println(workbook.getSheetName(cn) + " sheet의 row에 취득대상 cell수 : " + cells);
				for (int r = 0; r < rows; r++) {
					row = sheet.getRow(r); // row 가져오기
					if (row != null) {
						for (int c = 0; c < cells; c++) {
							cell = row.getCell(c);
							sido = row.getCell(1);
							if (cell != null) {
								String value = null;
								switch (cell.getCellType()) {//셀의 타입이
								case FORMULA: //수식일경우
									value = cell.getCellFormula();
									break;
								case NUMERIC: //숫자일경우
									value = "" + String.valueOf(new Double(cell.getNumericCellValue()).longValue());
									if(c==0)
										areaCodes = value;
									break;
								case STRING: //문자일경우
									value = "" + cell.getStringCellValue();
									if(sido.getStringCellValue().contains(sidoName)) { // 시 도
										if(c==3 && value.contains(areaName)) {  //법정동 
										areaCode = Long.parseLong(areaCodes)/100000; // 지역코드
										}
									}
									break;
								case BLANK: //빈공간일경우
									value = "[null 아닌 공백]";
									break;
								case ERROR:
									value = "" + cell.getErrorCellValue();
									break;
								default:
								}
							//	System.out.print(value + "\t");
							} else {
							//	System.out.print("[null]\t");
							}
						} // for(c) 문
					//	System.out.print("\n");
					}
				} // for(r) 문
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
}