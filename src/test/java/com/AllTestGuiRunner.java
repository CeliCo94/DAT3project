package com;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestExecutionSummary;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import javax.swing.*;
import java.awt.*;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class AllTestGuiRunner {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage("com"))
                .build();

        SummaryGeneratingListener summaryListener = new SummaryGeneratingListener();
        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(summaryListener);
        launcher.execute(request);

        TestExecutionSummary summary = summaryListener.getSummary();
        SwingUtilities.invokeLater(() -> showDialog(summary));
    }

    private static void showDialog(TestExecutionSummary summary) {
        long total = summary.getTestsFoundCount();
        long passed = summary.getTestsSucceededCount();
        long failed = summary.getTestsFailedCount();
        long skipped = summary.getTestsSkippedCount();

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.append("Total: " + total + "\n");
        area.append("Passed: " + passed + "\n");
        area.append("Failed: " + failed + "\n");
        area.append("Skipped: " + skipped + "\n\n");

        if (!summary.getFailures().isEmpty()) {
            area.append("Failures:\n");
            summary.getFailures().forEach(f ->
                    area.append("- " + f.getTestIdentifier().getDisplayName() + ": " +
                            (f.getException() != null ? f.getException().getMessage() : "no message") + "\n"));
        }

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(null, scroll,
                failed == 0 ? "All tests passed" : "Some tests failed",
                failed == 0 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}