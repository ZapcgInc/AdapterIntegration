package com.zap.hai.transformers

import com.zap.hai.agoda.model.Benefit
import com.zap.hai.eps.{EpsAmenity, EpsAmenityBuilder}

trait BenefitXfmr extends AgodaToEpsXfmr[Benefit, EpsAmenity] {

  override def transform(benefit: Benefit): EpsAmenity = {
    val builder = new EpsAmenityBuilder()
    builder.withId(benefit.id.toString)
    builder.withName(benefit.name)
    builder.build()

  }

}
