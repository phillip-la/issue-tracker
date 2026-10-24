import { useEffect, useState } from 'react'
import './App.css'
import axios from 'axios'

import IssueForm from './components/IssueForm'

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

  async function handleCreateIssue(newIssue) {
    try {
      const response = await axios.post('/api/issues', newIssue)
      setIssues(prevIssues => [...prevIssues, response.data])
      return response.data
    } catch (err) {
      console.error('Failed to create issue:', err)
      throw(err)
    }
  }

  return (
    <div>
      <h1>Phillip's Issue Tracker</h1>

      <IssueForm onCreateIssue={handleCreateIssue}/>

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
