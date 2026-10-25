import { useEffect, useState } from 'react'
import './App.css'
import axios from 'axios'

import IssueForm from './components/IssueForm'
import IssueList from './components/IssueList'

function App() {
  const [issues, setIssues] = useState([])
  const [editingIssue, setEditingIssue] = useState(null)

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

  function handleEditIssue(issue) {
    setEditingIssue(issue)
  }

  async function handleUpdateIssue(id, updatedIssue) {
    try {
      const response = await axios.put(`/api/issues/${id}`, updatedIssue)

      setIssues(prevIssues =>
        prevIssues.map(issue =>
          issue.id === id ? response.data : issue
        )
      )

      setEditingIssue(null)
    } catch (error) {
      console.error('Failed to update issue: ', error)
      throw error
    }
  }

  return (
    <div className="container">
      <h1 className="main-heading">Unofficial IBM Issue Tracker</h1>
      
      <div className="app-grid">
        <aside className="form-panel">
          {editingIssue && (
            <p className="editing-status-messsage">Currently editing: {editingIssue.title}</p>
          )}

          <IssueForm 
            key={editingIssue?.id ?? 'new'}
            onCreateIssue={handleCreateIssue}
            editingIssue={editingIssue}
            onUpdateIssue={handleUpdateIssue}
          />
        </aside>

        <main className="issues-panel">
          <IssueList
            issues={issues}
            onDeleteIssue={handleDeleteIssue}
            onEditIssue={handleEditIssue}
          />
        </main>
      </div>
    </div>
  )
}

export default App
