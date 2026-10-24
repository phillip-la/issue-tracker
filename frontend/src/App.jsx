import { useEffect, useState } from 'react'
import './App.css'
import axios from 'axios'

import IssueForm from './components/IssueForm'
import IssueList from './components/IssueList'

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
      console.error('Failed to create issue: ', err)
      throw(err)
    }
  }

  async function handleDeleteIssue(id) {
    try {
      await axios.delete(`/api/issues/${id}`)

      setIssues(prevIssues =>
        prevIssues.filter(issue => issue.id !== id)
      )
    } catch (error) {
      console.error('Failed to delete issue: ', error)
    }
  }

  return (
    <div>
      <h1>Phillip's Issue Tracker</h1>

      <IssueForm onCreateIssue={handleCreateIssue}/>

      <IssueList
        issues={issues}
        onDeleteIssue={handleDeleteIssue}
      />
    </div>
  )
}

export default App
