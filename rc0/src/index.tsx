/**
 * Seas
 *
 * Copyright 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { AppRouter } from 'app/AppRouter';
import { generateEntryPoint } from 'entryPointFactory';

generateEntryPoint(AppRouter);

/*

import React from 'react';
import ReactDom from 'react-dom';
import 'handsontable/dist/handsontable.full.css';


import { HotTable } from '@handsontable/react';
import {DialogForm} from 'app/components/DialogForm'

const data = [
  ['', 'Tesla', 'Mercedes', 'Toyota', 'Volvo'],
  ['2019', 10, 11, 12, 13],
  ['2020', 20, 11, 14, 13],
  ['2021', 30, 15, 12, 13]
];


function View(){
/*	return <HotTable data={data} colHeaders={true} rowHeaders={true} width="600" height="300" licenseKey="non-commercial-and-evaluation"  />

	return <DialogForm render={()=><div>321312</div>}><a>aaa</a></DialogForm>
}



ReactDom.render(<View />,document.getElementById('root'))*/