# lentil
Simple file based modeling tool.

## git から clone して eclipse にインポートするには

1. clone したトップ・ディレクトリで `.\gradlew.bat eclipse` を実行。
1. eclipse 用の設定ファイル群 ( `.project` や `.classpath` ) が作られているのを確認。
1. eclipse から普通に「既存のプロジェクトをワークスペースへ」のインポート操作を行う。

## eclipse 上で gradle のタスクを実行するには

1. インポートしたプロジェクトの右ボタン・メニューから `構成` > `gradle ネーチャーの追加` を選択。
1. `ウインドウ` > `ビューの表示` > `その他...` メニューの `gradle` > `gradle タスク` を選択。
1. 開いた「Gradle タスク」ビューで適当なタスクを選んで、右ボタン・メニューかダブル・クリックして実行。
