package org.example;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.fixes.SuggestedFix;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.sun.source.tree.AnnotationTree;

import static com.google.errorprone.matchers.Matchers.isType;

@AutoService(BugChecker.class)
@BugPattern(
        name = "BeforeCheck",
        summary = "JUnit 4's @Before is replaced by JUnit 5's @BeforeEach",
        severity = BugPattern.SeverityLevel.SUGGESTION
)
public class BeforeCheck extends BugChecker implements BugChecker.AnnotationTreeMatcher {
    private static final Matcher<AnnotationTree> matcher = isType("org.junit.Before");

    @Override
    public Description matchAnnotation(AnnotationTree annotationTree, VisitorState visitorState) {
        if (!matcher.matches(annotationTree, visitorState)) {
            return Description.NO_MATCH;
        }
        return describeMatch(annotationTree, SuggestedFix.replace(annotationTree, "org.junit.jupiter.api.BeforeAll"));
    }
}