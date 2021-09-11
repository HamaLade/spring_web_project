package com.springweb.notice.config.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

import static com.springweb.notice.config.validation.ValidationGroups.*;

@GroupSequence({Default.class, NotEmptyGroup.class, SizeGroup.class, EmailGroup.class,})
public interface ValidationSequence {
}
