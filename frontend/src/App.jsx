import { useEffect, useState } from 'react'
import './App.css'
import axios from 'axios'

function App() {
  const [issues, setIssues] = useState([])

  useEffect(() => {
    axios.get('api/issues')
      .then(res => {
        setIssues(res.data)
      })
      .catch(err => {
        console.error('Failed to fetch issues: ', err)
      })
  }, [])

  return (
    <div>
      <h1>Phillip's Issue Tracker</h1>

      <ul>
        {
          issues.map(
            issue => (
              <li key={issue.id}>
                {issue.title} - {issue.priority}
              </li>
            )
          )
        }
      </ul>
    </div>
  )
}

export default App
