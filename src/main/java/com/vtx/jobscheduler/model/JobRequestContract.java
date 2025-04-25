package com.vtx.jobscheduler.model;

import com.vtx.jobscheduler.annotation.ValidJobContract;
import lombok.Data;

// custom validation annotation
@ValidJobContract
@Data
public class JobRequestContract extends Contract {
}
