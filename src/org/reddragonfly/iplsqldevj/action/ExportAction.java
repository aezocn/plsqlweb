package org.reddragonfly.iplsqldevj.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.bean.BaisWorkBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportAction extends BaseAction {

    public String execute() throws Exception {
        try {
            ActionContext ctx = ActionContext.getContext();
            HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
            HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);

            String[] sqlNum = request.getParameterValues("sqlNum");
            BaisWorkBean baisWorkBean = new BaisWorkBean();
            baisWorkBean.getRequestLocal().set(request);
            baisWorkBean.GetResultList(sqlNum);

            exportExcel(baisWorkBean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SUCCESS;
    }

    public void exportExcel(BaisWorkBean baisWorkBean) {
        OutputStream os = null;
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            int rowIndex = 0;
            for (Object o : baisWorkBean.getList()) {
                Row row = sheet.createRow(rowIndex++);
                setRowWithStartCol(row, 0, ((List) o).toArray());
            }

            for(int i = 0; i < baisWorkBean.getList().size(); i++) {
                sheet.autoSizeColumn(i);
            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            String filename = sdf.format(date);
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");

            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void setRowWithStartCol(Row row, int startCol, Object[] values) {
        if(values != null) {
            for (int i = 0; i < values.length; i++) {
                Cell cell = row.createCell(i + startCol);

                String value = "";
                if(values[i] != null) {
                    if(values[i] instanceof Date) {
                        value = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format((Date) values[i]);
                    } else if(values[i] instanceof Long){
                        Long val = (Long) values[i];
                        cell.setCellValue(val.doubleValue());
                        continue;
                    } else {
                        value = String.valueOf(values[i]);
                    }
                }
                cell.setCellValue(value);
            }
        }
    }

}
