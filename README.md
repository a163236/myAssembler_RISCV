# myAssembler
ScalaによるRISC-Vのアセンブリを機械語にアセンブルするアセンブラ

## 概要
RV32Iのアセンブリを機械語に翻訳するプログラム。
詳しい説明は[こちら](https://qiita.com/a163236/items/1ea950f743457ede5d0b)(私のqiitaサイト)の方が分かりやすいです。
未実装の命令がいくつかあります。所々バグもあるかもしれません。

## 使い方
Assembly.txtに実行したいAssemblyを書く
sbtを使う場合はプロジェクト直下にAssembly.txtを置いて、/src/main/scalaにAssembler.scalaを配置する。
