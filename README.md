# myAssembler_RISCV
ScalaによるRISC-Vのアセンブリを機械語にするプログラム。

## 概要
RV32Iのアセンブリを機械語に翻訳するプログラム。
詳しい説明は[こちら](https://qiita.com/a163236/items/1ea950f743457ede5d0b)の方が分かりやすいです。
未実装の命令がいくつかあります。
このプログラムでは、

![](.README_images/85ed5ff0.png)

上のようなRV32Iのアセンブリを

![](.README_images/264ca7a8.png)

このように機械語に変換できます。また、

![](.README_images/5a305508.png)

このような出力にしてそのままChiselコードとして使えるような出力にもできます。

## 始め方
このリポジトリをクローンして、IntelliJ IDEAでそのフォルダを開くとすぐに使えます。

## 使い方
Assembly.asmに実行したいAssemblyをRV32Iで書く
/src/main/scala/Assembler/Assemblerが実行ファイルなのでこれを実行する。
そうすると、Assembly.asmに書いていたアセンブリがコンソールに出力される。

## 連絡先
[@a163236](https://twitter.com/a163236)まで
