# myAssembler
ScalaによるRISC-Vのアセンブリを機械語にアセンブルするアセンブラ

## 概要
RV32Iのアセンブリを機械語に翻訳するプログラム。
## 使い方
Assembly.txtに実行したいAssemblyを書く。
次に、Assembler.scalaとAssembly.txtを同じフォルダに入れて、Assembler.scalaを実行する。

sbtを使う場合はプロジェクト直下にAssembly.txtを置いて、/src/main/scalaにAssembler.scalaを配置する。
