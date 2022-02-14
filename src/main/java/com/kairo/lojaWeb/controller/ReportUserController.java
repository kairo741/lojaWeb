package com.kairo.lojaWeb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReportUserController {

    private final DataSource dataSource;

    @GetMapping("/reportUser")
    public void reportUser(HttpServletResponse response) {
        InputStream jasperFile = this.getClass().getResourceAsStream("/reports/user.jasper");
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=user_report.pdf");

            OutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        } catch (JRException | SQLException | IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @GetMapping("/reportCompra")
    public void reportCompra(HttpServletResponse response) {
        InputStream jasperFile = this.getClass().getResourceAsStream("/reports/compra.jasper");
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=compra_report.pdf");

            OutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        } catch (JRException | SQLException | IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }


}

