import API from '../API'

const host_url = API.scan
export const API_GET_TASKS_INFO = host_url+'/tasks'
export const API_CREATE_SCAN = host_url+'/quick'
export const API_GET_ISSUES = host_url+'/issues'
export const API_GET_SOURCES = host_url+'/sources'
export const API_GET_GENERALINFO = host_url + '/generalinfo'

export const isValidDate = (dateString) => {
    const regEx = /^\d{4}-\d{2}-\d{2}$/;
    if(!dateString.match(regEx)) return false;  // Invalid format
    const d = new Date(dateString);
    if(Number.isNaN(d.getTime())) return false; // Invalid date
    return d.toISOString().slice(0,10) === dateString;
  }
  
export const extractSourcePath = (key)=> key.split("\:")[1]