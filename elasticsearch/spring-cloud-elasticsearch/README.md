### 安装nodejs
**下载地址为：https://nodejs.org/dist/v6.11.0/node-v6.11.0-x64.msi**
```
E:\>cd nodejs

E:\nodejs>npm install grunt-cli
E:\nodejs
`-- grunt-cli@1.2.0
  +-- findup-sync@0.3.0
  | `-- glob@5.0.15
  |   +-- inflight@1.0.6
  |   | `-- wrappy@1.0.2
  |   +-- inherits@2.0.3
  |   +-- minimatch@3.0.4
  |   | `-- brace-expansion@1.1.8
  |   |   +-- balanced-match@1.0.0
  |   |   `-- concat-map@0.0.1
  |   +-- once@1.4.0
  |   `-- path-is-absolute@1.0.1
  +-- grunt-known-options@1.1.0
  +-- nopt@3.0.6
  | `-- abbrev@1.1.0
  `-- resolve@1.1.7

npm WARN enoent ENOENT: no such file or directory, open 'E:\nodejs\package.json'
npm WARN nodejs No description
npm WARN nodejs No repository field.
npm WARN nodejs No README data
npm WARN nodejs No license field.



E:\nodejs>npm install -g grunt-cli
C:\Users\Administrator\AppData\Roaming\npm\grunt -> C:\Users\Administrator\AppData\Roaming\npm\node_modules\grunt-cli\bin\grunt
C:\Users\Administrator\AppData\Roaming\npm
`-- grunt-cli@1.2.0
  +-- findup-sync@0.3.0
  | `-- glob@5.0.15
  |   +-- inflight@1.0.6
  |   | `-- wrappy@1.0.2
  |   +-- inherits@2.0.3
  |   +-- minimatch@3.0.4
  |   | `-- brace-expansion@1.1.8
  |   |   +-- balanced-match@1.0.0
  |   |   `-- concat-map@0.0.1
  |   +-- once@1.4.0
  |   `-- path-is-absolute@1.0.1
  +-- grunt-known-options@1.1.0
  +-- nopt@3.0.6
  | `-- abbrev@1.1.0
  `-- resolve@1.1.7


E:\nodejs>grunt -version
grunt-cli v1.2.0

E:\nodejs>

```
### elasticsearch 安装

下载地址为：https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.4.1.zip

直接解压
```
E:\elasticsearch-5.4.1\elasticsearch-5.4.1\bin>elasticsearch.bat
```
### elasticsearch-head 安装

从 github 上下载 地址为：https://github.com/mobz/elasticsearch-head.git
```
E:\>cd elasticsearch-head

E:\elasticsearch-head>npm install
npm WARN optional SKIPPING OPTIONAL DEPENDENCY: fsevents@^1.0.0 (node_modules\chokidar\node_modules\fsevents):
npm WARN notsup SKIPPING OPTIONAL DEPENDENCY: Unsupported platform for fsevents@1.1.2: wanted {"os":"darwin","arch":"any"} (current: {"os":"win32","arch":"x64"})
npm WARN elasticsearch-head@0.0.0 license should be a valid SPDX license expression



E:\elasticsearch-head>grunt server
Running "connect:server" (connect) task
Waiting forever...
Started connect web server on http://localhost:9100
```


### 配置
在 E:\elasticsearch-5.4.1\elasticsearch-5.4.1\config\elasticsearch.yml 配置
```
# 换个集群的名字，免得跟别人的集群混在一起
cluster.name: es-5.0-test

# 换个节点名字
node.name: node-101

# 修改一下ES的监听地址，这样别的机器也可以访问
network.host: 0.0.0.0

# 默认的就好
http.port: 9200

# 增加新的参数，这样head插件可以访问es
http.cors.enabled: true
http.cors.allow-origin: "*"
```

E:\elasticsearch-head\Gruntfile.js 中配置
```
connect: {
	server: {
		options: {
		
			port: 9100,
			base: '.',
			keepalive: true
		}
	}
}
```
添加 	hostname:'*',

E:\elasticsearch-head\_site\app.js 中配置
```
修改head的连接地址:

this.base_uri = this.config.base_uri || this.prefs.get("app-base_uri") || "http://localhost:9200";
把localhost修改成你es的服务器地址，如：

this.base_uri = this.config.base_uri || this.prefs.get("app-base_uri") || "http://10.10.10.10:9200";
```

















参考文档：

http://www.bysocket.com/?tag=elasticsearch