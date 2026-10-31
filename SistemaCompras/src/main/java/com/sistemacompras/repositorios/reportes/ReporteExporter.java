package com.sistemacompras.repositorios.reportes;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.File;

public class ReporteExporter {

    /**
     * Exporta un JasperPrint a PDF.
     */
    public static void exportarPDF(JasperPrint jasperPrint, File archivo) throws Exception {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(archivo));
        exporter.exportReport();
    }

    /**
     * Exporta un JasperPrint a Excel (.xlsx).
     */
    public static void exportarExcel(JasperPrint jasperPrint, File archivo) throws Exception {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(archivo));

        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
        config.setOnePagePerSheet(true);
        config.setDetectCellType(true);
        config.setCollapseRowSpan(false);
        config.setWhitePageBackground(false); // Esto también ayuda a evitar problemas de estilo

        // 👇 ESTO ES LA CLAVE: mapear el formato de fecha problemático
        java.util.Map<String, String> formatMap = new java.util.HashMap<>();
        formatMap.put("EEEEE dd MMMMM yyyy", "dd/MM/yyyy");
        config.setFormatPatternsMap(formatMap);

        exporter.setConfiguration(config);
        exporter.exportReport();
    }
}