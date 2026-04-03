package com.gradetracker.service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localisation demo
 * ResourceBundle, Locale, NumberFormat, and DateTimeFormatter
 */
public class LocaleService {

    private Locale currentLocale;
    private ResourceBundle bundle;

    public LocaleService() {
        setLocale(Locale.ENGLISH);
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
        this.bundle = ResourceBundle.getBundle("messages", locale);
    }

    public String getMessage(String key) {
        return bundle.containsKey(key) ? bundle.getString(key) : "[?" + key + "?]";
    }

    public String formatNumber(double number) {
        return NumberFormat.getNumberInstance(currentLocale).format(number);
    }

    public String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance(currentLocale).format(amount);
    }

    public String formatPercent(double fraction) {
        return NumberFormat.getPercentInstance(currentLocale).format(fraction);
    }

    public String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                .withLocale(currentLocale));
    }

    // Full demo
    public void demonstrateLocalisation() {
        System.out.println("\n=== A3: Localisation Demo ===\n");

        Locale[] locales = {
                Locale.ENGLISH,
                Locale.of("ga", "IE"),   // Irish
                Locale.FRENCH
        };

        double score = 88.5;
        double fee   = 12500.75;
        LocalDate examDate = LocalDate.of(2026, 4, 25);

        for (Locale loc : locales) {
            setLocale(loc);
            System.out.printf("── Locale: %s (%s) ──%n", loc.getDisplayName(), loc.toLanguageTag());
            System.out.println("  " + getMessage("app.title"));
            System.out.println("  " + getMessage("menu.welcome"));
            System.out.println("  " + getMessage("student.gpa_label") + " " + formatNumber(score));
            System.out.println("  " + getMessage("report.date") + " " + formatDate(examDate));
            System.out.println("  Fee: " + formatCurrency(fee));
            System.out.println("  Pass rate: " + formatPercent(0.875));
            System.out.println();
        }

        // Reset to English
        setLocale(Locale.ENGLISH);
    }
}
