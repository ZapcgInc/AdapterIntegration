package com.zap.hai.eps

case class EpsCancelPenalty(
                             currency: String,
                             start: String,
                             end: String,
                             amount: Double,
                             nights:String,
                             percent: Doubles
                           )


//    def this(p_currency:String, policyDate: PolicyDate)
//    {
//        this()
//        currency = p_currency
//        start = Option(policyDate.after).getOrElse("")
//        end = Option(policyDate.before).getOrElse("")
//        amount =  policyDate.rate.inclusive
//    }
