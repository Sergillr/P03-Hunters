package com.example.utils.io;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public final class PdfUnlocker {
    private PdfUnlocker() {
    }

    /**
     * Tests whether PDFBox can open the PDF with the supplied password.
     *
     * @param path     the PDF file
     * @param password the password to test
     * @return true if the PDF opens successfully, or false if the password is
     *         rejected
     * @throws IOException if the PDF cannot be read, parsed, or closed
     */
    public static boolean unlockPdf(Path path, String password) throws IOException {
        try (PDDocument document = Loader.loadPDF(path.toFile(), password)) {
            return true;
        } catch (InvalidPasswordException exception) {
            return false;
        }

    }
}