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

import { useLocation } from 'react-router-dom';
import Query from 'query-string';
import isPlainObject from 'lodash/isPlainObject';
const useRouteQuery = (key) => {

  const qs = Query.parse( useLocation().search?.substr?.(1) );

  const mc = {
  	get:function( _key ){
  		if(typeof _key === 'string'){
  			return qs[_key]
  		}
  		if(isPlainObject(_key)){
  			return qs[_key.key];
  		}

  		return null
  	},
  	has:function( _key){
  		const v = this.get(_key);
  		return v !== null && v !== undefined
  	}
  };
  


  if(!key) {
  	return mc;
  }

  return mc.get(key);
};

export default useRouteQuery;
