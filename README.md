[![Build Status](https://travis-ci.org/GoingOK/goingok-server.svg?branch=master)](https://travis-ci.org/GoingOK/goingok-server) 
[![https://img.shields.io/badge/license-Apache%202.0-blue.svg](https://img.shields.io/badge/license-Apache%202.0-lightgrey.svg)](http://www.apache.org/licenses/LICENSE-2.0)
<!-- [ ![bintray](https://api.bintray.com/packages/nlytx/nlytx_commons/nlytx_commons/images/download.svg?version=0.1.1) ](https://bintray.com/nlytx/nlytx_commons/nlytx_commons/0.1.1/link) -->

# goingok-server

This is the server side code for a full stack providing [GoingOK](http://goingok.org), a web application for personal reflective writing. The application was developed by [Andrew Gibson](http://andrewresearch.net) as part of his Doctoral research on Reflective Writing Analytics. It is now an open source project.
 
 This repo provides the REST API for [goingok-client](https://github.com/GoingOK/goingok-client).

### Quick Start

1. Ensure that you are running a postgreSQL server and that you have an empty GoingOK database

2. Change the name of ```application_conf_templ``` to ```aplication.conf``` ([src/main/resources/](src/main/resources)), and edit it to include *your* database settings.

3. Compile with ```sbt compile``` and run with ```sbt run```

4. Connecting to ```http://localhost:8080/v1/health``` should return json...

```json
{
  "message": "ok"
}
```

### Documentation

For more detailed documentation on both client and server components, see the current [docs](http://goingok.org/docs/).

### Submitting bugs and suggestions

Please use [GitHub issues](../../issues) to notify us of a bug or to request a new feature. Before adding a new request, *please* search the existing issues to see if there is one the same or similar to yours. If so, add a [reaction](//github.com/blog/2119-add-reactions-to-pull-requests-issues-and-comments) (like :+1: or :-1:) to the issue and post any additional relevant comments that will be helpful.

### Contributing

Contributions to the code are welcome. Please read [CONTRIBUTING](CONTRIBUTING.md) for more information.

### License

 > &copy; Andrew Gibson 2012-2017
 >
   > Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
   >
   > [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)
   >
   > Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.