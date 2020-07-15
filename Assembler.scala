import scala.io.Source

object Assembler extends App {
  val path1 = System.getProperty("user.dir") //現在のディレクトリパス
  val lines = Source.fromFile("Assembly.txt").getLines()
  var counter = 0
  lines.foreach { c =>

    // 2文字以上続くスペースを削除して、もろもろspaceに置き換えて、それからspace区切りでassに入れる
    val asm = c.replaceAll("[ |(|)|,]+", " ").trim.split("[ ]")
    val inst = asm(0) match{  // asm(0)はopcode
      // R形式
      case "srai" => "0100000"+imm(asm(3)).takeRight(5)+fivebitReg(asm(2))+"101"+fivebitReg(asm(1))+"0010011"
      case "add"  => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"000"+fivebitReg(asm(1))+"0110011"
      case "sub"  => "0100000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"000"+fivebitReg(asm(1))+"0110011"
      case "sll"  => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"001"+fivebitReg(asm(1))+"0110011"
      case "slt"  => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"010"+fivebitReg(asm(1))+"0110011"
      case "sltu" => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"011"+fivebitReg(asm(1))+"0110011"
      case "xor"  => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"100"+fivebitReg(asm(1))+"0110011"
      case "srl"  => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"101"+fivebitReg(asm(1))+"0110011"
      case "sra"  => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"101"+fivebitReg(asm(1))+"0110011"
      case "or"  => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"110"+fivebitReg(asm(1))+"0110011"
      case "and"  => "0000000"+fivebitReg(asm(3))+fivebitReg(asm(2))+"111"+fivebitReg(asm(1))+"0110011"
      // I形式
      case "jalr" => imm(asm(3)).takeRight(12)+fivebitReg(asm(2))+"000"+fivebitReg(asm(1))+"1100111"
      case "lb"   => imm(asm(2)).takeRight(12)+fivebitReg(asm(3))+"000"+fivebitReg(asm(1))+"0000011"
      case "lh"   => imm(asm(2)).takeRight(12)+fivebitReg(asm(3))+"001"+fivebitReg(asm(1))+"0000011"
      case "lw"   => imm(asm(2)).takeRight(12)+fivebitReg(asm(3))+"010"+fivebitReg(asm(1))+"0000011"
      case "mv"   => "000000000000"+fivebitReg(asm(2))+"000"+fivebitReg(asm(1))+"0010011"  // addi rd rs1
      case "li"   => imm(asm(2)).takeRight(12)+"00000"+"000"+fivebitReg(asm(1))+"0010011" // addi rd, rs, x0と同じ
      case "addi" => imm(asm(3)).takeRight(12)+fivebitReg(asm(2))+"000"+fivebitReg(asm(1))+"0010011"
      case "slti" => imm(asm(3)).takeRight(12)+fivebitReg(asm(2))+"010"+fivebitReg(asm(1))+"0010011"
      case "sltiu"=> imm(asm(3)).takeRight(12)+fivebitReg(asm(2))+"011"+fivebitReg(asm(1))+"0010011"
      case "xori" => imm(asm(3)).takeRight(12)+fivebitReg(asm(2))+"100"+fivebitReg(asm(1))+"0010011"
      case "ori" => imm(asm(3)).takeRight(12)+fivebitReg(asm(2))+"110"+fivebitReg(asm(1))+"0010011"
      case "andi" => imm(asm(3)).takeRight(12)+fivebitReg(asm(2))+"111"+fivebitReg(asm(1))+"0010011"
      // S形式
      case "sb"   => imm(asm(2)).takeRight(12).take(7)+fivebitReg(asm(1))+fivebitReg(asm(3))+"000"+imm(asm(2)).takeRight(5)+"0100011"
      case "sh"   => imm(asm(2)).takeRight(12).take(7)+fivebitReg(asm(1))+fivebitReg(asm(3))+"001"+imm(asm(2)).takeRight(5)+"0100011"
      case "sw"   => imm(asm(2)).takeRight(12).take(7)+fivebitReg(asm(1))+fivebitReg(asm(3))+"010"+imm(asm(2)).takeRight(5)+"0100011"
      // B形式         imm[12|10:5]                                       rs2                   rs1           funct3
      case "beq"  => imm(asm(3))(13)+imm(asm(3)).takeRight(11).take(6)+fivebitReg(asm(2))+fivebitReg(asm(1))+"000"+imm(asm(3)).takeRight(5).take(4) +imm(asm(3))(12)+"1100011"
      case "bne"  => imm(asm(3))(13)+imm(asm(3)).takeRight(11).take(6)+fivebitReg(asm(2))+fivebitReg(asm(1))+"001"+imm(asm(3)).takeRight(5).take(4) +imm(asm(3))(12)+"1100011"
      case "blt"  => imm(asm(3))(13)+imm(asm(3)).takeRight(11).take(6)+fivebitReg(asm(2))+fivebitReg(asm(1))+"100"+imm(asm(3)).takeRight(5).take(4) +imm(asm(3))(12)+"1100011"
      case "bge"  => imm(asm(3))(13)+imm(asm(3)).takeRight(11).take(6)+fivebitReg(asm(2))+fivebitReg(asm(1))+"101"+imm(asm(3)).takeRight(5).take(4) +imm(asm(3))(12)+"1100011"
      case "bltu" => imm(asm(3))(13)+imm(asm(3)).takeRight(11).take(6)+fivebitReg(asm(2))+fivebitReg(asm(1))+"110"+imm(asm(3)).takeRight(5).take(4) +imm(asm(3))(12)+"1100011"
      case "bgeu" => imm(asm(3))(13)+imm(asm(3)).takeRight(11).take(6)+fivebitReg(asm(2))+fivebitReg(asm(1))+"111"+imm(asm(3)).takeRight(5).take(4) +imm(asm(3))(12)+"1100011"
      // U形式
      case "lui"  => imm(asm(2))+fivebitReg(asm(1))+"0110111"
      case "auipc"=> imm(asm(2))+fivebitReg(asm(1))+"0010111"
      // J形式
      case "j"    => imm(asm(1)).head+imm(asm(1)).takeRight(11).init+imm(asm(1))(12)+imm(asm(1)).tail.drop(12)+"00000"+"1101111"  // jal x0 offset と同じ
      case "jal"  => imm(asm(2)).head+imm(asm(2)).takeRight(11).init+imm(asm(1))(12)+imm(asm(1)).tail.drop(12)+imm(asm(2))+"1101111"
      //
      case "fence"=> ""
      case "ecall" => "000000000000"+"00000"+"000"+"00000"+"1110011"
      case "ebreak"=> "000000000001"+"00000"+"000"+"00000"+"1110011"
      case _ => ""
    }

    //println(inst)
    println("memory.write("+counter+".U, \"b"+inst+"\".U(32.W))")
    counter += 4
  }
  // spとかa0とかをレジスタ番号にする
  def fivebitReg(name:String): String ={
    val tmp = name match {
      case "zero" => 0
      case "ra" => 1
      case "sp" => 2
      case "gp" => 3
      case "tp" => 4
      case "t0" => 5
      case "t1" => 6
      case "t2" => 7
      case "s0" => 8
      case "fp" => 8
      case "s1" => 9
      case "a0" => 10
      case "a1" => 11
      case "a2" => 12
      case "a3" => 13
      case "a4" => 14
      case "a5" => 15
      case "a6" => 16
      case "a7" => 17
      case "s2" => 18
      case "s3" => 19
      case "s4" => 20
      case "s5" => 21
      case "s6" => 22
      case "s7" => 23
      case "s8" => 24
      case "s9" => 25
      case "s10" => 26
      case "s11" => 27
      case "t4" => 29
      case "t5" => 30
      case "t6" => 31

      case "x0" => 0
      case "x1" => 1
      case "x2" => 2
      case "x3" => 3
      case "x4" => 4
      case "x5" => 5
      case "x6" => 6
      case "x7" => 7
      case "x8" => 8
      case "x9" => 9
      case "x10" => 10
      case "x11" => 11
      case "x12" => 12
      case "x13" => 13
      case "x14" => 14
      case "x15" => 15
      case "x16" => 16
      case "x17" => 17
      case "x18" => 18
      case "x19" => 19
      case "x20" => 20
      case "x21" => 21
      case "x22" => 22
      case "x23" => 23
      case "x24" => 24
      case "x25" => 25
      case "x26" => 26
      case "x27" => 27
      case "x28" => 28
      case "x29" => 29
      case "x30" => 30
      case "x31" => 31
      case "x32" => 32

    }
    // 上のを2進数の文字列にして、5文字に足りないのを0パディング
    "%5s".format(tmp.toBinaryString).replace(" ","0")
  }

  def imm(name:String): String ={
    if(name.toInt<0) name.toInt.toBinaryString.takeRight(20)  // 負のとき
    else "%20s".format(name.toInt.toBinaryString).replace(" ", "0")
  }
}
