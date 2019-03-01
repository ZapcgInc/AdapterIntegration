package com.zap.hai.eps

case class EpsAmenity(id: String, name: String)

class  EpsAmenityBuilder {

  private var id: String = _
  private var name: String = _

  def withId(id:String)={
    this.id = id
    this
  }
  def withName(name:String)={
    this.name = name
    this
  }
  def build() = EpsAmenity(id, name)

}